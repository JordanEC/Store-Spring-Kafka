FROM node:alpine as build
WORKDIR /app
COPY package.json /app
RUN yarn install && yarn cache clean
COPY . /app
CMD ["yarn", "run", "build"]

# production environment
FROM nginx:stable-alpine
#COPY --from=build /app/build /usr/share/nginx/html
COPY --from=build /app/build /var/www
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]