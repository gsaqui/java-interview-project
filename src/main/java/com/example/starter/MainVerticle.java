package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        // add a handler which sets the request body on the RoutingContext.
        router.route().handler(BodyHandler.create());

        router.get("/")
                // this handler will ensure that the Pojo is serialized to json
                // the content type is set to "application/json"
                .respond(
                        ctx -> {
                            String animalType = ctx.queryParams().get("type");
                            Integer numOfAnimals = Integer.parseInt(ctx.queryParams().get("numOfAnimals"));

                            return Future.succeededFuture(this.data());
                        });

        router.post("/")
                .respond(
                        ctx -> {
                            String data = ctx.getBodyAsString();
                            return Future.succeededFuture(data);
                        });

        server.requestHandler(router).listen(8888);
    }

    private List<Animal> data() {
        List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal(1, 5.0, "dog", "bulldog"));
        animals.add(new Animal(2, 10, "dog", "poodle"));
        animals.add(new Animal(3, 15, "dog", "beagle"));
        animals.add(new Animal(4, 5, "cat", "persian"));
        animals.add(new Animal(5, 100, "cat", "russian blue"));
        animals.add(new Animal(6, 15, "cat", "scottish fold"));
        animals.add(new Animal(7, 15, "cat", "siamese"));
        animals.add(new Animal(8, 100, "cow", "angus"));
        animals.add(new Animal(9, 200, "cow", "hereford"));
        animals.add(new Animal(10, 1000, "cow", "guernsey"));
        return animals;
    }
}
