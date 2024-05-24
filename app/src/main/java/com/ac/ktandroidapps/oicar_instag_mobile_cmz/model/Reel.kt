package com.ac.ktandroidapps.oicar_instag_mobile_cmz.model

class Reel {

    var caption : String? = null
    var reelUrl : String? = null
    var time : String? = null
    var uid : String? = null
    var user : User? = null
    var documentId :String? = null

    constructor()

    constructor(reelUrl: String?, caption: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
    }

    constructor(reelUrl: String?, caption: String?, uid: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.uid = uid
    }
    constructor(reelUrl: String?, caption: String?, time:String?, uid: String?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.time = time
        this.uid = uid
    }

    constructor(reelUrl: String?, caption: String?, uid: String?, time: String?, user: User?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.uid = uid
        this.time = time
        this.user = user
    }

    constructor(reelUrl: String?, caption: String?, uid: String?, time: String?, user: User?, documentId:String?) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.uid = uid
        this.time = time
        this.user = user
        this.documentId = documentId
    }
}