Установка - 

Поставить Java OpenJDK 11
Поставить Maven: https://maven.apache.org/install.html

Если все сделали правильно: в терминале пишем команду mvn spring-boot:run и система заведеться. ссылка на посмотреть тут - http://localhost:8080/

Для работы с интерфейсом - 
каждая страница(вид) это отдельный контроллер, смотрим комменты в файле HomeController.java
шаблоны кладутся в папку templates
чтобы пилить их на модули(что бы сделать прям шаблон-шаблон) используем следующее:
<header th:insert="./fragments/navbar.html :: nav"> </header>
th:insert указывает файл с фрагментов, после : указываем ИМЯ фрагмента
в файле КОТОРЫЙ мы вызываем как фрагмент должен быть элемент с указателем - 
th:fragment="nav" (смотрим соотв. файл)


ПОЛЕЗНЫЕ ССЫЛКИ:
https://spring.io/guides/gs/serving-web-content/
https://www.baeldung.com/spring-thymeleaf-fragments

Документация API - 
https://docs.esi.evetech.net/


SSO API Example (АХТУНГ! Это НЕ СПРИНГ ТАМ НИЖЕ. Надо прочитать код, понять что он делает и использовать это в наших контроллерах)
Больше примеров тут - https://github.com/burberius/eve-esi/blob/master/src/test/java/net/troja/eve/esi/api/auth/SsoAuthTest.java
/**
    * This main method can be used to generate a refresh token to run the unit
    * tests that need authentication. It is also an example how to use SSO in
    * an implementation.
    *
    * More description is in the README.md
    *
    * @param args
    *            The client id.
    * @throws IOException
    * @throws URISyntaxException
    * @throws net.troja.eve.esi.ApiException
    */
public static void main(final String... args) throws IOException, URISyntaxException, ApiException {
    final String state = "somesecret";
    final ApiClient client;
    if (args.length == 1) {
        client = new ApiClientBuilder().clientID(args[0]).build();
    } else {
        if (System.getenv().get(SSO_CLIENT_ID) != null) {
            client = new ApiClientBuilder().clientID(System.getenv().get(SSO_CLIENT_ID)).build();
        } else {
            System.err.println("ClientId missing");
            System.exit(-1);
            client = new ApiClientBuilder().build();
        }
    }
    final OAuth auth = (OAuth) client.getAuthentication("evesso");
    final Set<String> scopes = SsoScopes.ALL;
    String redirectUri;
    if (System.getenv().get("SSO_CALLBACK_URL") != null) {
        redirectUri = System.getenv().get("SSO_CALLBACK_URL");
    } else {
        redirectUri = "http://localhost";
    }
    final String authorizationUri = auth.getAuthorizationUri(redirectUri, scopes, state);
    System.out.println("Authorization URL: " + authorizationUri);
    Desktop.getDesktop().browse(new URI(authorizationUri));

    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Code from Answer: ");
    final String code = br.readLine();
    auth.finishFlow(code, state);
    System.out.println("Refresh Token: " + auth.getRefreshToken());
}