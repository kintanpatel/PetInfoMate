package com.manageairproducts.petinfomate.data.database


class AnimalRepo(
    private val dao: AnimalDao
) {

    suspend fun insert(item: AnimalEntity) {
        dao.add(item)
    }

    suspend fun delete(item: AnimalEntity) {
        dao.delete(item)
    }

    suspend fun getAll(): List<AnimalEntity> {
        return dao.getAll()
    }


}