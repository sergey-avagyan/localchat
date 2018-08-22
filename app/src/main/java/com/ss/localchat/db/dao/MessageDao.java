package com.ss.localchat.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ss.localchat.db.entity.Message;

import java.util.List;
import java.util.UUID;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM messages " +
            "ORDER BY date ASC")
    LiveData<List<Message>> getAllMessages();

    @Query("SELECT * FROM messages " +
            "WHERE sender_id = :user_id OR receiver_id = :user_id " +
            "ORDER BY date ASC")
    LiveData<List<Message>> getMessagesWith(UUID user_id);

    @Query("SELECT * FROM messages " +
            "WHERE sender_id = :user_id OR receiver_id = :user_id " +
            "ORDER BY date DESC " +
            "LIMIT 1")
    LiveData<Message> getLastMessage(UUID user_id);

    @Query("SELECT * FROM messages " +
            "WHERE (sender_id = :user_id OR receiver_id = :user_id) AND is_read = :is_read " +
            "ORDER BY date ASC")
    LiveData<List<Message>> getReadOrUnreadMessagesWith(UUID user_id, boolean is_read);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Message... messages);

    @Insert
    void insert(Message message);

    @Delete
    void delete(Message message);

    @Query("DELETE FROM messages WHERE sender_id = :user_id OR receiver_id = :user_id")
    void clearHistory(UUID user_id);
}