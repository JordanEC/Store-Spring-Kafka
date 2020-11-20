FROM node:alpine as build
WORKDIR /app
COPY package.json /app
RUN yarn install && yarn cache clean
COPY . /app
CMD ["yarn", "run", "build"]

# production environment
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]