package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.FormTypeInterface
import com.example.herbario_nacional.models.funghi.ShapeType

interface FormTypeRepository {
    suspend fun getFormType(): List<ShapeType>
}

class FormTypeRepositoryImpl(private val formTypeService: FormTypeInterface): FormTypeRepository{
    override suspend fun getFormType(): List<ShapeType> {
        return formTypeService.requestFormType()
    }
}