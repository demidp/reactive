package reactive.model;

import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Product {
    private ObjectId id;
    private String name;
    private Double cost;

    public Product(String name, Double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Product(Document doc) {
        this.id = doc.getObjectId("_id");
        this.name = doc.getString("name");
        this.cost = doc.getDouble("cost");
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    public Document asDocument() {
        return new Document("_id", new BsonObjectId().getValue()).append("cost", cost).append("name", name);
    }

    public Product() {
    }

    public ObjectId getId() {
        return id;
    }

    public Product setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Double getCost() {
        return cost;
    }

    public Product setCost(Double cost) {
        this.cost = cost;
        return this;
    }
}
