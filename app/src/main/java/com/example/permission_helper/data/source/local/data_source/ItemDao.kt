/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.permission_helper.data.source.local.data_source

import android.arch.persistence.room.*
import com.example.permission_helper.data.model.Item

/**
 * Data Access Object for the items table.
 */
@Dao
interface ItemDao {

    /**
     * Select all items from the items table.
     *
     * @return all items.
     */
    @Query("SELECT * FROM ITEM")
    fun getitems(): List<Item>

    /**
     * Select a item by id.
     *
     * @param itemId the item id.
     * @return the item with itemId.
     */
    @Query("SELECT * FROM ITEM WHERE entryid = :itemId")
    fun getitemById(itemId: String): Item?

    /**
     * Insert a item in the database. If the item already exists, replace it.
     *
     * @param item the item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertitem(item: Item)

    /**
     * Update a item.
     *
     * @param item item to be updated
     * @return the number of items updated. This should always be 1.
     */
    @Update
    fun updateitem(item: Item): Int

    /**
     * Update the complete status of a item
     *
     * @param itemId    id of the item
     * @param completed status to be updated
     */
    @Query("UPDATE items SET completed = :completed WHERE entryid = :itemId")
    fun updateCompleted(itemId: String, completed: Boolean)

    /**
     * Delete a item by id.
     *
     * @return the number of items deleted. This should always be 1.
     */
    @Query("DELETE FROM items WHERE entryid = :itemId")
    fun deleteitemById(itemId: String): Int

    /**
     * Delete all items.
     */
    @Query("DELETE FROM items")
    fun deleteitems()

    /**
     * Delete all completed items from the table.
     *
     * @return the number of items deleted.
     */
    @Query("DELETE FROM items WHERE completed = 1")
    fun deleteCompleteditems(): Int
}