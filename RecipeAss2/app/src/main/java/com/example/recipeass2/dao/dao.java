//package com.example.recipeass2.dao;
//
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//public interface dao {
//
//        @Query("SELECT * FROM customer ORDER BY last_name ASC")
//        LiveData<List<Customer>> getAll();
//
//        @Query("SELECT * FROM customer WHERE uid = :customerId LIMIT 1")
//        Customer findByID(int customerId);
//
//        @Insert
//        void insert(Customer customer);
//
//        @Delete
//        void delete(Customer customer);
//
//        @Update
//        void updateCustomer(Customer customer);
//
//        @Query("DELETE FROM customer")
//        void deleteAll();
//
//
//}
