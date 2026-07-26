package com.sarana_NIS.newTown_backend.model.entity.library;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(length = 200)
    private String author;

    @Column(length = 100)
    private String publisher;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(length = 50)
    private String edition;

    @Column(length = 50)
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "total_copies")
    private Integer totalCopies = 1;

    @Column(name = "available_copies")
    private Integer availableCopies = 1;  // ✅ This field exists

    @Column(name = "shelf_location", length = 50)
    private String shelfLocation;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Checkout> checkouts = new ArrayList<>();

    // ❌ NO status field
    // ❌ NO location field (use shelfLocation)
    // ❌ NO category field (use genre)

    // Constructors
    public Book() {
        this.totalCopies = 1;
        this.availableCopies = 1;
        this.isActive = true;
        this.checkouts = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }

    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }

    public String getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(String shelfLocation) { this.shelfLocation = shelfLocation; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public List<Checkout> getCheckouts() { return checkouts; }
    public void setCheckouts(List<Checkout> checkouts) { this.checkouts = checkouts; }

    // Helper methods
    public boolean isAvailable() {
        return availableCopies != null && availableCopies > 0 && isActive != null && isActive;
    }

    public void decrementAvailableCopies() {
        if (availableCopies != null && availableCopies > 0) {
            availableCopies--;
        }
    }

    public void incrementAvailableCopies() {
        if (availableCopies != null) {
            availableCopies++;
        }
    }

    public void addCheckout(Checkout checkout) {
        if (this.checkouts == null) {
            this.checkouts = new ArrayList<>();
        }
        this.checkouts.add(checkout);
        checkout.setBook(this);
    }
}