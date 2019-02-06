package com.flickr.client

import java.lang.Exception

data class Error (val code:Int?=null,val message:String? = null,val exception: Exception?= null)