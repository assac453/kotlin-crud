package com.assac453.crud.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskDTORequest(
    @JsonProperty("name")
    var name: String,
    @JsonProperty("description")
    var description: String,
    @JsonProperty("done")
    var done: Boolean
)
