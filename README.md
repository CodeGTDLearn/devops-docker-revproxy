### Project Index

1. Complete API (SpringMVC)
   1. Hikary
   2. JPA
   3. PropertiesFiles profiles


2. Router - Reverse Proxy/Load Balancing
   1. NGINX:
      1. Dockerfile 
      2. nginx.conf:
         1. All phases commented sequentially
   2. SRC (JavaApp - Servlets):
         1. IpRandom
         2. IpSequential


3. Containerization: 
   1. docker-file 
   2. Compose:
      1. NGINX
      2. Servlet
   3. Scaling:
      1. static
      2. dynamic


4. NGINX
   * Seguranca
     * Reverse Proxy
       * Evitar Ataques/Vetores Basicos
     * Mitiga DDoS:
       * Pois melhora a gestao de requests
   * SSL
   * Escalabilidade:
     * Load Balancing
       * Mesmo local
       * Local diversos
   * Cache:
     * Cacheia URLs cacheaveis
   * Acumulacao:
     * Aninhamento de Proxies
   * Compressao de dados:
     * Trafego mais rapido (Escala)
     * Custo reduzido por reducao de trafego
     * Liberar Thread mais rapido (mitiga DDoS)
     * Evita a 'compressao dentro da API' o que gasta mais cpu