USER ?= "arobinson"
# This is from the internet, it's gotta be right.  Right?
# https://stackoverflow.com/questions/18136918/how-to-get-current-relative-directory-of-your-makefile
SERVICE_NAME := "pdf-platform"
VERSION ?= $(shell git describe --tags --always --abbrev=7 --dirty=-dirty-$(USER)-$(shell date -u +"%Y%m%dT%H%M%SZ"))
DOCKER_REPO := "akrobinson74/prototypes20210905"
service-up-compose = docker-compose -f docker/base.yml

dump:
	@echo "SERVICE_NAME : $(SERVICE_NAME)"
	@echo "DOCKER_REPO  : $(DOCKER_REPO)"
	@echo "USER         : $(USER)"
	@echo "VERSION      : $(VERSION)"
	@echo "BUILD LATEST : docker build -f docker/app/Dockerfile -t $(DOCKER_REPO) ./docker/app"
	@echo "BUILD DOCKER : docker tag $(DOCKER_REPO):latest $(DOCKER_REPO):$(VERSION)"
	@echo "               docker tag $(DOCKER_REPO):latest $(DOCKER_REPO):dev"
	@echo "               docker push $(DOCKER_REPO):$(VERSION)"
	@echo "               docker push $(DOCKER_REPO):dev"

clean:
	rm -rf docker/app/build 
	./gradlew clean

build: clean
	./gradlew bootJar

build-docker: ecr
	@echo "SERVICE_NAME : $(SERVICE_NAME)"
	@echo "USER         : $(USER)"
	@echo "VERSION      : $(VERSION)"
	@echo "DOCKER_REPO  : $(DOCKER_REPO)"
	docker build -f docker/app/Dockerfile -t $(DOCKER_REPO) ./docker/app

build-local:
	docker build -f docker/app/Dockerfile -t latest_local ./docker/app

build-local-and-run: build-local
	docker run -d --name pdf-platform_latest_local -p 9000:9000 latest_local

compose.deps.down:
	$(service-up-compose) down

compose.deps.up: build
	$(service-up-compose) build

compose.service.up: build
	$(service-up-compose) build
	$(service-up-compose) up -d

compose.service.down:
	$(service-up-compose) down

# Tags and push "dev" image to $DOCKER_REPO
release: dump build-docker
	docker tag $(DOCKER_REPO):latest $(DOCKER_REPO):$(VERSION)
	docker tag $(DOCKER_REPO):latest $(DOCKER_REPO):dev
	docker push $(DOCKER_REPO):$(VERSION)
	docker push $(DOCKER_REPO):dev

.PHONY: dump