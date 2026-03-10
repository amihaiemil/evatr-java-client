# 🇩🇪 eVatR Client library for Java

[![DevOps By Rultor.com](http://www.rultor.com/b/amihaiemil/evatr-java-client)](http://www.rultor.com/p/amihaiemil/evatr-java-client)
[![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)


Checks a VAT-ID using the eVatR REST-API of the German Federal Central Tax Office (Bundeszentralamt für Steuern, BZSt)


To get the latest release from Maven Central, simply add the following to your ``pom.xml``:

```xml
<dependency>
    <groupId>com.amihaiemil.web</groupId>
    <artifactId>evatr-java-client</artifactId>
    <version>0.0.4</version>
</dependency>
```

If you use Gradle, add this to your dependencies:

```gradle
implementation group: 'com.amihaiemil.web', name: 'evatr-java-client', version: '0.0.4'
```

The releases are also available on [Github Packages](https://github.com/amihaiemil/evatr-java-client/packages)!

----

# How to use it

```java
   final EvatrApi evatr = new RestfulEvatrApi(); //RestfulEvatrApi is the only entry-point in the library
   final EvatrVatStatus response = evatr.vatApi().verifyExternalVatNumber("DE123456789", "ATU12345678");
   System.out.println(resp);
   /*
       EvatrVatStatusResponse{
           technicalId = 33f5c03f66be3345,
           technicalStatusCode = evatr-0000,
           callTimeStamp = 2026-03-10T09:59:40.990920910+01:00,
           message = Die angefragte Ust-IdNr. ist zum Anfragezeitpunkt gültig.,
           validFrom = null,
           validTo = null,
           companyName = ,
           companyStreet = ,
           companyCity = ,
           companyZipCode = 
       }
    */

    final EvatrSupportApi support = evatr.supportApi(); //utility Support-API from evatr
    //...
```

We strive to handle the 4xx and 5xx responses from the Server -- ideally, you should never get an Exception.

