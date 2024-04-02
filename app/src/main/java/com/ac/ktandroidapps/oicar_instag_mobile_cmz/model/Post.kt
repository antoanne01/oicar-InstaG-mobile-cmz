package com.ac.ktandroidapps.oicar_instag_mobile_cmz.model

class Post {
    var postUrl : String? = null
    var caption : String? = null
    var uid : String? = null
    var time : String? = null
    var user : User? = null

    constructor()

    constructor(postUrl: String?, caption: String?) {
        this.postUrl = postUrl
        this.caption = caption
    }

    constructor(postUrl: String?, caption: String?, uid: String?, time: String?) {
        this.postUrl = postUrl
        this.caption = caption
        this.uid = uid
        this.time = time
    }

    constructor(postUrl: String?, caption: String?, uid: String?, time: String?, user: User?) {
        this.postUrl = postUrl
        this.caption = caption
        this.uid = uid
        this.time = time
        this.user = user
    }
}