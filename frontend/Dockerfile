FROM node:boron
RUN mkdir -p /usr/src
WORKDIR /usr/src
# Install app dependencies
COPY package.json /usr/src/
RUN npm install
CMD [ "npm", "start" ]