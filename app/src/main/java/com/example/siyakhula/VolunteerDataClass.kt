package com.example.siyakhula
import com.google.firebase.database.PropertyName
data class VolunteerDataClass(
    var key: String? = null,
    var Email: String = "",
    var FirstName: String = "",
    var PhoneNumber: String = ""

) {
    constructor() : this("", "", "")
}