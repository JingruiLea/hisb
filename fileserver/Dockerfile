FROM node:8.9-alpine
MAINTAINER xuranus

ADD . /home/his/fileserver/
WORKDIR /home/his/fileserver
RUN yarn install
EXPOSE 8082
CMD ["yarn", "start"]