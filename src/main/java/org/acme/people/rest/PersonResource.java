package org.acme.people.rest;

import java.util.List;

import org.acme.people.model.DataTable;
import org.acme.people.model.EyeColor;
import org.acme.people.model.Person;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/person")
@ApplicationScoped
public class PersonResource {

    private static final Logger LOG = Logger.getLogger(PersonResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getAll() {
		return Person.listAll();
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getById(@PathParam("id") Long id, @HeaderParam("token") String token) {
		LOG.info("Datos de entrada: ".concat(id.toString()).concat(" token: ").concat(token));

		return Person.findById(id);
	}
	// TODO: add basic queries
	@GET
	@Path("/eyes/{color}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> findByColor(@PathParam(value = "color") EyeColor color) {
		return Person.findByColor(color);
	}

	@GET
	@Path("/birth/before/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getBeforeYear(@PathParam(value = "year") int year, @HeaderParam("token") String token ) {
		return Person.getBeforeYear(year);
	}

	// TODO: add datatable query
	@GET
	@Path("/datatable")
	@Produces(MediaType.APPLICATION_JSON)
	public DataTable datatable(@QueryParam(value = "draw") int draw, @QueryParam(value = "start") int start,
			@QueryParam(value = "length") int length, @QueryParam(value = "search[value]") String searchVal

	) {
		// TODO: Begin result
		DataTable result = new DataTable();
		result.setDraw(draw);
		// TODO: Filter based on search
		PanacheQuery<Person> filteredPeople;

		if (searchVal != null && !searchVal.isEmpty()) {
			filteredPeople = Person.<Person>find("name like :search", Parameters.with("search", "%" + searchVal + "%"));
		} else {
			filteredPeople = Person.findAll();
		}
		// TODO: Page and return
		int page_number = start / length;
		filteredPeople.page(page_number, length);

		result.setRecordsFiltered(filteredPeople.count());
		result.setData(filteredPeople.list());
		result.setRecordsTotal(Person.count());

		return result;
	}

	// TODO: Add lifecycle hook
	@Transactional
	void onStart(@Observes StartupEvent ev) {
		
//		for (int i = 0; i < 100; i++) {
//			String name = CuteNameGenerator.generate();
//			LocalDate birth = LocalDate.now().plusWeeks(Math.round(Math.floor(Math.random() * 40 * 52 * -1)));
//			EyeColor color = EyeColor.values()[(int) (Math.floor(Math.random() * EyeColor.values().length))];
//			Person p = new Person();
//			p.birth = birth;
//			p.eyes = color;
//			p.name = name;
//			Person.persist(p);
//		}
	}
}