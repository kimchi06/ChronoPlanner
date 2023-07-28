package cmpt276.project.djjnp.projectdjjnp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cmpt276.project.djjnp.projectdjjnp.models.Event;
import cmpt276.project.djjnp.projectdjjnp.models.EventRepository;
import cmpt276.project.djjnp.projectdjjnp.service.EventService;




@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired(required = true)
    private EventRepository eventRepo;


    private final EventService eventService;


    
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    
    
    @GetMapping("/event")
    public List<Event> getEvents() {
        if (eventRepo.count() > 0) {
            return eventRepo.findAll();
        }

        return null;
    }
    
    @PostMapping("/calendar/add")
    public ResponseEntity<String> saveEvent(@RequestBody Event event) {
        // Save the event using the EventService
        eventService.saveEvent(event);

        return ResponseEntity.ok("Event saved successfully.");
    }

    @DeleteMapping("/{sid}")
    public ResponseEntity<String> deleteEvent(@PathVariable int sid) {
        // Check if the event exists in the database
        Optional<Event> eventOptional = eventRepo.findById(sid);
        if (!eventOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Delete the event using the EventService
        eventService.deleteEventById(sid);

        return ResponseEntity.ok("Event deleted successfully.");
    }

    @PostMapping("/display/add")
    public ResponseEntity<String> saveMarker(@RequestBody Event event) {

        eventService.saveEvent(event);

        return ResponseEntity.ok("Marker saved successfully.");
    }
}
