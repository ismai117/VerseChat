package com.im.versechat.model.util

interface EntityMapper <Entity,DomainModel> {

    fun toDomainModel(entity: Entity): DomainModel

    fun fromDomainModel(entity: DomainModel): Entity

}