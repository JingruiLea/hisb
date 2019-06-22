FROM node:8.9-alpine
MAINTAINER xuranus

ADD . /home/his/middleware/
WORKDIR /home/his/middleware
RUN yarn install
EXPOSE 8081
CMD ["yarn", "start"]