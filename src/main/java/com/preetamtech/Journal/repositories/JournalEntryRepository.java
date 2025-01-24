package com.preetamtech.Journal.repositories;

import com.preetamtech.Journal.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {

}
