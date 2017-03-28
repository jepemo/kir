package com.github.jepemo.kir.sampleapp

import com.github.jepemo.kir.sampleapp.view.Home
import com.github.jepemo.kir.web.http.Route

@Route("/")
fun home() = Home()

