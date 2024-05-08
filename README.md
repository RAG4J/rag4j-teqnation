# RAG4J - Teqnation
Workshop specific repository for the Teqnation conference. If you join our workshop, please prepare by reading the README en setup your environemnt.

## Setting up your environment

## Java
Use one of the installers like:
- [jenv](https://www.jenv.be)
- [sdkman](https://sdkman.io)
- [jbang](https://www.jbang.dev)

Oh and we use maven, you can install it that if you do not have access to it yet. Or use the provided version. Which 
is the easiest way to get started.

[Apache Maven](https://maven.apache.org)

Use an IDE of your choice, we use IntelliJ IDEA, but something like Eclipse or VS Code will work as well.

## Test the installation
To test your installation, run the following command:
```shell
mvn clean package
```

or
```shell
./mvnw clean package
```

You can run a java class with the following command:
```shell
mvn exec:java -Dexec.mainClass="org.rag4j.AppStep0CheckEnvironment"
```

The format for running a single test is:
```shell
mvn -Dtest=TestClassName#methodName test
```

## Loading API keys
We try to limit accessing Large Language Models and vector stores to a minimum. This is enough to learn about all the
elements of the Retrieval Augmented Generation framework, except for the generation part. In the workshop we use the
LLM of Open AI, which is not publicly available. We will provide you with a key to access it.

Please use this key for the workshop only, and limit the amount of interaction, or we get blocked for exceeding our
limits. The API key is obtained through a remote file, which is encrypted. Of course you can also use your own key if
you have it.

### Environment variables
The easiest way to load the API key is to set an environment variable for each required key. The names of the
environment variables are:
- `openai_api_key`
- `weaviate_api_key`
- `weaviate_url`

Another way of doing this is through the file on your classpath in the resources folder. This file is
called `env.properties`. It contains the following lines:
```properties
openai_api_key=sk-...
weaviate_api_key=...
weaviate_url=...
```

The final method is to provide an environment variable or a line in the mentioned file containing the following line:
```properties
secret_key=...
```
This secret key is used to decrypt the remote file containing the API keys. We will provide the value for this key
during the workshop.
