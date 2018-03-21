package com.gilbord

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
@EnableAutoConfiguration
class Application {
    companion object {

        @JvmStatic
        public fun main(args: Array<String>){
            SpringApplication.run(Application::class.java, *args)
        }

    }
}