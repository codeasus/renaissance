package codeasus.projects.renaissance.model.features.contact

data class Contact(
    val id: Long,
    val rawID: Long,
    val firstName: String,
    val lastName: String,
    val numbers: List<String>
)