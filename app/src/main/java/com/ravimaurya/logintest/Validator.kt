package com.ravimaurya.logintest



fun String.isNameValid(): Boolean{
    return this.length > 2
}
fun String.isPasswordValid(): Boolean{
    return this.length > 3
}