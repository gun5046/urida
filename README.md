# 특화 프로젝트 D202
![016](/uploads/3b89cafc449d6c09f62a7778af931cc8/016.png)
# :rocket: 프로젝트 소개
## :pencil2: 점점 증가하는 다문화 학생들을 위한 교육 앱 'URIDA' :pencil2:
- 한국어ㆍ베트남어ㆍ중국어ㆍ영어 4개 국어를 지원하여 **약 70%** 의 다문화 학생들이 사용할 수 있습니다.
- 구글 TextToSpeech API 사용하여 지문 및 단어를 클릭하면 해당 단어를 **음성**으로 읽어줍니다.
- stt : kospeech 의 deep speech 2 모델을 활용하여 ai_hub의 한국어 발화 음성을 학습하였습니다. 음성으로 단어를 찾아줍니다.
- 이미지 분류: cnn 의 개선 방식인 resnet50 모델과 quickdraw dataset 을 활용하여서 200개 이상의 카테고리를 95% 이상 val_acc 로 예측하여줍니다.

- - -
### :memo: '단어학습'을 통해 기초 단어를 익혀보아요!
- **단어익히기**: 유닛1~유닛6까지 원하는 유형의 단어를 학습할 수 있습니다.
- **퀴즈 풀기**: `단어익히기`를 통해 학습한 단어를 기반으로 네 가지 유형의 퀴즈들을 풀어볼 수 있습니다.
- **다시 풀어보기**: `퀴즈풀기`에서 틀린 문제를 확인할 수 있습니다. 정답 확인 버튼을 통해 답을 확인할 수 있습니다.

- - -
### :camera: '사진찍기' 기능을 사용하여 카메라에 감지된 물체들을 탐지해보아요!
- **실시간으로 확인하기**: 카메라를 사용하여 실시간으로 물체의 이름을 확인할 수 있습니다.
- **사진찍기**: 카메라로 사진을 촬영하여 해당 이미지에 나타난 사물 목록을 확인할 수 있습니다.
- **갤러리에서 불러오기**: 갤러리에서 불러온 이미지에 나타난 사물 목록을 확인할 수 있습니다.

- - -
### :mag: '찾아보기' 기능을 통해 그림과 음성을 활용하여 원하는 단어를 찾아보아요!
- **그림으로 찾기**: 그림을 그려 해당 그림이 어떤 단어인지 확인할 수 있습니다.
- **음성으로 찾기**: 녹음한 음성이 어떤 단어를 의미하는지 이미지와 함께 확인할 수 있습니다.

- - -
### :sparkles: '커뮤니티'를 통해 함께 이야기를 나누어보아요!
- **자유 게시판**: 게시글 작성을 하여 사람들과 소통할 수 있습니다.
- **그림 게시판**: `찾아보기 - 그림으로 찾기`에서 인식하지 못한 그림을 공유하여, 다른사람들과 함께 이야기 나눠볼 수 있습니다.

- - -
# 📚 기술스택

| 분야 | 사용기술 |
| --- | --- |
| FrontEnd | Android(Kotlin), MVVM |
| BackEnd | SpringBoot,MSA|
| Database | MySQL |
| DevOps | AWS EC2, GPU, Docker, Jenkins|
| Tool | Jira, Notion, IntelliJ, AndroidStudio, GitLab |
| Design | Figma |

- - -
# :bulb: 아키텍처
![Architecture1.png](./Architecture1.png)
- - - 
# :chart_with_upwards_trend: ERD

![image.png](./image.png)
- - -
# :see_no_evil: 기능 엿보기 (GIF)

### 회원가입
![회원가입](https://user-images.githubusercontent.com/68943993/231978062-f19ecba4-892c-4133-9d69-eddeae25db36.gif)

### 단순 로그인
![단순_로그인](https://user-images.githubusercontent.com/68943993/231978075-ff69ddea-cb50-4d16-bfc8-c6304a0fd020.gif)

### 언어 변경
![언어_변경_기능](https://user-images.githubusercontent.com/68943993/231978406-abe73f7a-79be-4051-9f03-99796583c2e9.gif)

### 단어 익히기
![단어_익히기](https://user-images.githubusercontent.com/68943993/231977846-b7aa5803-acac-4276-95a0-efa451f49215.gif)

### 단어 퀴즈
![단어_퀴즈](https://user-images.githubusercontent.com/68943993/231978561-e6db1aff-a436-41b3-a6b1-d7c2e10ab5ff.gif)

### 다시 풀어보기
![다시_풀어보기](https://user-images.githubusercontent.com/68943993/231978686-8119b8b5-73ba-4f77-ab32-1e6042411751.gif)

### 실시간으로 확인하기
![실시간_캡셔닝](https://user-images.githubusercontent.com/68943993/231978734-52db73cd-4bf9-4450-851b-4c4c90a841b4.gif)

### 사진 찍기
![사진으로_찾기](https://user-images.githubusercontent.com/68943993/231978738-f43ed3e8-002f-477e-9e1a-c1f8ed0ea7f6.gif)

### 갤러리에서 불러오기
![갤러리에서_불러오기](https://user-images.githubusercontent.com/68943993/231978779-a8c634e8-33c7-4ddf-af67-a54aaeb1c0e7.gif)

### 그림으로 찾기
![그림으로_찾기](/uploads/f0f01f215c7af75879702c271aa5da3f/그림으로_찾기.gif)

### 음성으로 찾기
![음성으로_찾기](/uploads/eb9ed9e22862ac73e87b56f680d70777/음성으로_찾기.gif)

### 게시글 수정, 댓글, 카테고리 확인
![게시글_수정__댓글__카테고리별](https://user-images.githubusercontent.com/68943993/231979353-c7f81bbb-22c0-43ff-a893-47fab700dc63.gif)

### 그림게시판 확인 및 작성
![그림게시판_확인_및_작성](https://user-images.githubusercontent.com/68943993/231979201-767006cc-015e-4551-9e0a-6871deecdabc.gif)

### 댓글 수정, 게시글 작성 및 확인
![댓글_수정__게시글_작성_및_확인](https://user-images.githubusercontent.com/68943993/231979108-0412f719-1bf7-4603-a9b2-09a6e015f175.gif)
