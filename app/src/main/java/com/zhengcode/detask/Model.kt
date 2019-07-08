package com.zhengcode.detask

data class Task(var name: String)
data class SkillStub(var name: String)
data class TraitStub(var name: String)

object Supplier {
    var skillStubs = listOf(
        SkillStub("Guitar"),
        SkillStub("Yoyo"),
        SkillStub("Piano")
    )

    var traitStubs = listOf(
        TraitStub("Kind"),
        TraitStub("Charming"),
        TraitStub("Handsome"),
        TraitStub("Strong")
    )

    val tasks = listOf<Task>(
        Task("Teach me Android development"),
        Task("Buy me flowers"),
        Task("Drive me to school"),
        Task("Do my homework"),
        Task("Fix my merge conflict"),
        Task("Mitochondria is the powerhouse of a cell"),
        Task("Teach me guitar"),
        Task("Babysit my siblings"),
        Task("Read my book"),
        Task("Teach me Android development"),
        Task("Buy me flowers"),
        Task("Drive me to school"),
        Task("Do my homework"),
        Task("Fix my merge conflict"),
        Task("Mitochondria is the powerhouse of a cell"),
        Task("Teach me guitar"),
        Task("Babysit my siblings"),
        Task("Read my book")
    )
}
