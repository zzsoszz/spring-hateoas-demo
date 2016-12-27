http://localhost:8080/h2-console

http://localhost:8080/jhoeller/bookmarks


注册到hal-browser
Register a bean that implements a ResourceProcessor<RepositoryLinksResource> and you can add links to your custom controller to the root resource, and the HAL Browser will see it.
public class RootResourceProcessor implements ResourceProcessor<RepositoryLinksResource> {
@Override
public RepositoryLinksResource process(RepositoryLinksResource resource) {
    resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RevisionController.class).getRevisions(null).withRel("revisions")));
    return resource;
    }
}




http://docs.spring.io/spring-data/rest/docs/2.4.4.RELEASE/reference/html/#customizing-sdr.customizing-json-output
16.4. Customizing the JSON output
Sometimes in your application you need to provide links to other resources from a particular entity. For example, a Customer response might be enriched with links to a current shopping cart, or links to manage resources related to that entity. Spring Data REST provides integration with Spring HATEOAS and provides an extension hook for users to alter the representation of resources going out to the client.

16.4.1. The ResourceProcessor interface
Spring HATEOAS defines a ResourceProcessor<> interface for processing entities. All beans of type ResourceProcessor<Resource<T>> will be automatically picked up by the Spring Data REST exporter and triggered when serializing an entity of type T.

For example, to define a processor for a Person entity, add a @Bean to your `ApplicationContext like the following (which is taken from the Spring Data REST tests):

@Bean
public ResourceProcessor<Resource<Person>> personProcessor() {

   return new ResourceProcessor<Resource<Person>>() {

     @Override
     public Resource<Person> process(Resource<Person> resource) {

      resource.add(new Link("http://localhost:8080/people", "added-link"));
      return resource;
     }
   };
}