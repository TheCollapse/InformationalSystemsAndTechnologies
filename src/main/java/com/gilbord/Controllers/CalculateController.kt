package com.gilbord.Controllers

import com.gilbord.Models.Model
import com.gilbord.Models.PDF
import com.gilbord.Services.CalculateService
import org.apache.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@RestController
class CalculateController {

    val log = Logger.getLogger(CalculateController::class.java.name)
    @Autowired
    lateinit var calculateService: CalculateService

    @CrossOrigin
    @RequestMapping(value = ["/performance"],
            method = [RequestMethod.POST])
    public fun calculatePerformance(@RequestBody model: Model): ResponseEntity<Any> {
        return if (calculateService.validate(model)) ResponseEntity(calculateService.performanceFunction(model), HttpStatus.OK)
        else ResponseEntity(Error("Data doesn't valid"), HttpStatus.BAD_REQUEST)
    }

    @CrossOrigin
    @RequestMapping(value = ["/pdf"],
            method = [RequestMethod.POST])
    public fun getPDF(): ResponseEntity<Any>{
        log.info("pdf")
        CalculateService.calculatedModel?.let{
            val pdf = PDF(it)
            val headers = HttpHeaders()
            headers.contentType = MediaType.parseMediaType("application/pdf")
            val filename = "output.pdf"
            headers.setContentDispositionFormData(filename, filename)
            headers.cacheControl = "must-revalidate, post-check=0, pre-check=0"
            return ResponseEntity(pdf.generate(), headers, HttpStatus.OK)
        }
        return ResponseEntity("Model didn't calculated for report", HttpStatus.BAD_REQUEST)
    }

    @CrossOrigin
    @RequestMapping(value = ["/calculate"],
            method = [RequestMethod.POST])
    public fun calculateModel(@RequestBody model: Model): ResponseEntity<Any>{
        return if (calculateService.validate(model)) ResponseEntity(calculateService.calculate(model), HttpStatus.OK)
        else ResponseEntity("Data doesn't valid", HttpStatus.BAD_REQUEST)
    }

}