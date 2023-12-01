package br.com.alura.screenmatch.service;

public interface IDataConverter {
    <T> T fetchData(String json, Class<T> tClass);
    /* 1. "<T>" indica um parâmetro de tipo genérico. Isso significa que a interface "IDataConverter" é genérica e
    * pode trabalhar com diferentes tipos de dados.
    * 2. "T fetchData(String json, Class<T> tClass)" é um método genérico em relação ao tipo "T". Ele aceita dois
    * parâmetros:
    * - "String json": uma string representando dados em formato JSON que serão processados pelo método.
    * - "Class<T> tClass": um objeto da classe "Class" que representa o tipo do objeto que o método deve retornar.
    * Isso permite que o método saiba qual tipo de objeto deve ser criado e retornado.
    * Basicamente, essa interface é um contrato que define um método genérico para converter (ou desserializar) dados
    * JSON em objetos Java. */
}
