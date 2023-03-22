import os


def stringParser() :
    change = ['.','!','?',',','\'']

    file = open("./dataset/kspon/data/transcript.v.1.4.txt",encoding='UTF8')
    audio_path = []
    transcripts = []
    while True : 
        line = file.readline()

        string = line.split('|')
        if not line : break
        # print(string[1])
        # dict[string[0]]=string[1]
        audio_path.append(string[0])

        for idx in range(5) :
            string[2] = string[2].replace(change[idx],'')
            
        
        transcripts.append(string[2])

    file.close()
    return audio_path,transcripts
