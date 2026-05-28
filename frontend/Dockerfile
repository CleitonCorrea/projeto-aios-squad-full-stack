FROM node:22-alpine AS build
WORKDIR /workspace
ARG API_BASE_URL=http://localhost:8081
ENV NG_APP_API_BASE_URL=${API_BASE_URL}
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/aiox-frontend/browser /usr/share/nginx/html
EXPOSE 80
