package com.example.restfullsimple


class Percent {
    var name : String? = null
    var threshold : Int? = null


    constructor(name: String, threshold: Int) {
        require(name != "" && threshold in (0..100))
        this.name = name
        this.threshold = threshold

    }


}