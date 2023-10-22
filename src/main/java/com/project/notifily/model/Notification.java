package com.project.notifily.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String recipient;
    private String date_entrance;
    private String data_create;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "checkpoint_id", referencedColumnName = "id")
    private Checkpoint checkpoint;
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<Product> products;
    private String extra;

    public Notification() {
    }

    public Notification(String sender, String recipient, String date_entrance, String data_create, Status status, Checkpoint checkpoint, List<Product> products, String extra) {
        this.sender = sender;
        this.recipient = recipient;
        this.date_entrance = date_entrance;
        this.data_create = data_create;
        this.status = status;
        this.checkpoint = checkpoint;
        this.products = products;
        this.extra = extra;
    }

    public String getProductsString(){
        List<Product> list = (this.getProducts());
        StringBuilder sb = new StringBuilder();
        for( Product el: list){
            sb.append(el.getName()).append("; ");
        }
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getDate_entrance() {
        return date_entrance;
    }

    public void setDate_entrance(String date_entrance) {
        this.date_entrance = date_entrance;
    }

    public String getData_create() {
        return data_create;
    }

    public void setData_create(String data_create) {
        this.data_create = data_create;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
