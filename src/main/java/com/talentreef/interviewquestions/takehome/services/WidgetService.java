package com.talentreef.interviewquestions.takehome.services;

import com.talentreef.interviewquestions.exceptions.ResourceNotFoundException;
import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WidgetService {

    private final WidgetRepository widgetRepository;

    @Autowired
    private WidgetService(WidgetRepository widgetRepository) {
        Assert.notNull(widgetRepository, "widgetRepository must not be null");
        this.widgetRepository = widgetRepository;
    }

    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    public Widget getWidget(String name) {
        return widgetRepository.findById(name).orElseThrow(ResourceNotFoundException::new);
    }

    public Widget updateWidget(String name, Widget widget) {
        Widget dbWidget = getWidget(name);
        dbWidget.setDescription(widget.getDescription());
        dbWidget.setPrice(widget.getPrice());
        return widgetRepository.save(dbWidget);
    }

    public Widget createWidget(Widget widget) {
        Optional<Widget> exists = widgetRepository.findById(widget.getName());
        if (exists.isPresent()) {
            log.debug("Exists");
            throw new ResourceNotFoundException();
        }
        return widgetRepository.save(widget);
    }

    public Widget deleteWidget(String name) {
        Optional<Widget> exists = widgetRepository.findById(name);
        return exists.map(
                        wid -> {
                            widgetRepository.deleteById(name);
                            return exists.get();
                        })
                .orElseThrow(ResourceNotFoundException::new);
    }
}
