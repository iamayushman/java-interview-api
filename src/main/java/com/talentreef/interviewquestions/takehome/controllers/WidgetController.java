package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.exceptions.ResourceNotFoundException;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

    private final WidgetService widgetService;

    public WidgetController(WidgetService widgetService) {
        Assert.notNull(widgetService, "widgetService must not be null");
        this.widgetService = widgetService;
    }

    @GetMapping
    public ResponseEntity<List<Widget>> getAll() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Widget> findOne(@PathVariable String name) {
        log.debug("Get {}", name);
        return ResponseEntity.ok(widgetService.getWidget(name));
    }

    @PostMapping
    public ResponseEntity<Widget> create(@RequestBody @Valid Widget widget) {
        log.debug("Create {}", widget);
        return ResponseEntity.ok(widgetService.createWidget(widget));
    }

    @PutMapping("/{name}")
    public ResponseEntity<Widget> update(
            @PathVariable String name, @RequestBody @Valid Widget widget) {
        log.debug("Update {} -> {}", name, widget);
        return ResponseEntity.ok(widgetService.updateWidget(name, widget));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Widget> delete(@PathVariable String name) {
        log.debug("Delete {}", name);
        return ResponseEntity.ok(widgetService.deleteWidget(name));
    }
}
