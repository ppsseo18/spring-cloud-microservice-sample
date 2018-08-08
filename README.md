# Sample Microservice
스프링 클라우드 라이브러리 예제 프로젝트입니다.  

테스트 시 config server -> discovery service -> api-gateway -> 나머지 순서대로 구동합니다.

## Config Server
Config Server는 각 서비스들의 설정 정보를 제공해줍니다. git repository를 설정 저장소로 사용하며 이 예제 프로젝트는 현재 git repo가 저장소로 사용되고 있습니다.

## Eureka
Eureka server는 Discovery Service로서 이 프로젝트에 구현되어있고 여러 마이크로 서비스를 이에 등록하면, 마이크로 서비스간 통신 시 각 서비스의 정확한 경로를 제공해줍니다. 

## Zuul
Zuul은 API Gateway를 구현하는 라이브러리 입니다. 각 서비스를 해당 Zuul 서비스에 Zuul에 등록하면, Zuul서버에서 해당 서비스에 해당하는 mapping url을 호출하면 Zuul은 연결된 Eureka 서버에서 해당 service의 주소를 얻어와 서비스를 호출해줍니다. Zuul은 또한 로드밸런싱 기능 또한 수행합니다.

## Ribbon
Ribbon은 client-side 로드밸런싱 라이브러리입니다. 각 마이크로서비스에서 다른 서비스를 호출 시 Ribbon을 통한 템플릿을 사용하면 자동으로 Eureka에서 경로를 찾아와 손쉽게 api 호출을 가능하게 해줍니다. order-service에 예제를 구현하였습니다.

