package sk96.dev.kik.bot.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import sk96.dev.kik.bot.message.*;
import sk96.dev.kik.bot.utils.JsonSearch;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.io.File;
import java.util.Random;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger L = Logger.getTextFileLogger("HttpServer");
    private final String apiUrl;
    private final String base64Key;
    private final int port;

    public HttpServerVerticle(String apiUrl, String base64Key, int port) {
        this.apiUrl = apiUrl;
        this.base64Key = base64Key;
        this.port = port;
    }

    @Override
    public void start(Future<Void> future) {
        L.info("Creating a HTTP server");

        final HttpServer server = vertx.createHttpServer();
        final Router router = Router.router(vertx);
        final Route route = router.route();

        route.produces("json").handler(ctx -> {
            int randomId = new Random().nextInt();
            L.info("Received HTTP request id of " + randomId + " from address " + ctx.request().host());

            final HttpServerResponse response = ctx.response();
            response.putHeader("Authorization", "Basic " + base64Key);

            final HttpServerRequest request = ctx.request();
            request.bodyHandler(body -> vertx.executeBlocking(x -> {
                final String json = body.toString();
                if(json.length() <= 0) {
                    if(!request.uri().endsWith(".jpg")) {
                        x.fail("Received a non-image GET request: " + request.uri());
                        response.setStatusCode(404);
                        response.end();
                        return;
                    }
                    if(request.uri().length() > 2) {
                        if(!new File("." + request.uri().replaceAll("%20", " ")).exists()) {
                            x.fail("Cannot serve request, file not found: " + "." + request.uri());
                            response.setStatusCode(404);
                            response.end();
                            return;
                        }
                        response.sendFile("." + request.uri().replaceAll("%20", " "));
                        response.setStatusCode(200);
                        response.end();
                        x.complete();
                        return;
                    }

                    x.fail("Received empty message, ignoring id " + randomId);
                    response.setStatusCode(200);
                    response.end();
                    return;
                }

                /* Set HTTP OK to avoid Kik from re-sending data that we already acknowledged */
                response.setStatusCode(200);
                response.end();

                L.trace("Received: " + body.toString());

                if(null == json) {
                    if(json.length() <= 0) {
                        L.warning("Received non-json message, ignoring id " + randomId);
                    } else {
                        L.warning("Received unknown data from id: " + randomId + ", data: " + json);
                    }
                    x.fail("Could not parse data as JSON");
                    return;
                }

                if(!JsonSearch.has(json, "type")) {
                    x.fail("Could not find type tag in received json.");
                    return;
                }

                MessageType type = null;

                final String requestType = JsonSearch.get(json, "type");

                for (MessageType messageType : MessageType.values()) {
                    if (messageType.toString().equalsIgnoreCase(requestType)) {
                        type = messageType;
                        break;
                    }
                }

                if (null == type) {
                    x.fail("No suitable message type found for: " + requestType);
                    return;
                }

                L.info("Received type: " + requestType);

                final MessageDecoder decoder = Codec.decoders.get(type);

                if (null == decoder) {
                    x.fail("No decoder found for type: " + type.toString());
                    return;
                }

                final MessageHandler handler = Codec.handlers.get(type);

                if (null == handler) {
                    x.fail("No handler found for type: " + type.toString());
                    return;
                }

                final Message decodedMessage = decoder.decode(json);

                if (null == decodedMessage) {
                    x.fail("Failed to decode json: " + json);
                    return;
                }

                final Message message = handler.handle(decodedMessage);

                if (null == message) {
                    x.fail("Null Message object received from MessageHandler");
                    return;
                }

                final MessageEncoder encoder = Codec.encoders.get(message.getClass());

                if (null == encoder) {
                    x.fail("Failed to encode " + message.getClass());
                    return;
                }

                final String encodedJson = encoder.encode(message);

                switch (message.type) {
                    case TEXT:
                    case PICTURE:
                    case LINK:
                    case VIDEO:
                    case START_CHATTING:
                    case SCAN:
                    case STICKER:
                    case IS_TYPING:
                    case DELIVERY_RECEIPT:
                    case READ_RECEIPT:
                    case FRIEND_PICKER:
                        Server.postOnNewThread(base64Key, apiUrl + "/message", encodedJson);
                        x.complete();
                        break;
                    default:
                        L.warning("Not yet implemented to send out " + message.type.toString() + " messages");
                        break;
                }
                L.info("Finished handling request: " + randomId);
            }, res -> {
                if (res.failed()) {
                    L.error("Failed to handle HTTP request: " + res.cause().getMessage());
                } else if (res.succeeded()) {
                    L.info("Successfully handled HTTP request");
                }
            }));
        });

        /*
         * listen(port) seems to sometimes take a while so we move it away from the main thread
         */
        vertx.executeBlocking(x -> server.requestHandler(router::accept).listen(port, res -> {
            if (res.succeeded()) {
                L.info("HTTP server online port " + port);
                x.complete();
            } else {
                L.error("Could not start HTTP server on port " + port + ", cause: " + res.cause().getMessage());
                x.fail(res.cause());
            }
        }), res -> {
            if (res.failed()) {
                L.error("Failed to load HTTP server");
            } else if (res.succeeded()) {
                L.info("HTTP server has finished loading");
            }
        });
    }
}