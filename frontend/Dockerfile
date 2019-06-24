ARG apiServerURL
ARG fileServerURL 

FROM node:latest
MAINTAINER xuranus

WORKDIR /home/his/frontend

#RUN yarn install
#RUN echo "export default {apiServer:'$apiServerURL',fileUploadServer:'$fileServerURL'}" > ./src/global/Config.jsx
#RUN yarn build

RUN npm install -g serve
COPY ./build /usr/share/nginx/html
#RUN cp ./build/* /usr/share/nginx/html

CMD ["serve","-s","build","-p","80"]

EXPOSE 80