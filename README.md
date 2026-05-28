# projeto-aios-squad-full-stack

Projeto full stack enterprise-grade para cadastro e gestao de produtos com autenticacao JWT, microsservicos, arquitetura hexagonal e comunicacao assincrona via RabbitMQ.

## Arquitetura C4

![Diagrama C4 da arquitetura](img/BCO.0704c39b-b358-4622-ab32-1a28e48924d3.png)

## Execucao local

Infraestrutura compartilhada:

```bash
docker compose up -d
```

Backend:

```bash
cd backend
docker compose up -d --build
```

Frontend:

```bash
cd frontend
docker compose up -d --build
```

## Portas

- Frontend: `http://localhost:8080`
- User API: `http://localhost:8081`
- Product API: `http://localhost:8082`
- Notification API: `http://localhost:8083`
- RabbitMQ Management: `http://localhost:15672`
- MailHog: `http://localhost:8025`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`
