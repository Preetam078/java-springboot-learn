package com.preetamtech.Journal.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
//Added @Data (Lombok annotation to get the automatic "Getter" and "Setter")
@Data
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
}
