import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

import nltk
from nltk.stem.porter import PorterStemmer
nltk.download('punkt')

import sys
search_term = sys.argv[1] if len(sys.argv) > 1 else ""

df = pd.read_csv("spotify_millsongdata.csv")
df.isnull().sum()

df.head(5000).reset_index(drop=True).to_csv("test.csv")
df = pd.read_csv("test.csv")
df['text'] = df['text'].str.lower().replace(r'^\w\s', ' ').replace(r'\n', ' ', regex = True)
# print(df[df["song"]=="Sea Of Dreams"])

stemmer = PorterStemmer()

def tokenization(txt):
    tokens = nltk.word_tokenize(txt)
    stemming = [stemmer.stem(w) for w in tokens]
    return " ".join(stemming)

df['text'] = df['text'].apply(lambda x: tokenization(x))
# print(df.head())
tfidvector = TfidfVectorizer(analyzer='word',stop_words='english')
matrix = tfidvector.fit_transform(df['text'])

similarity = cosine_similarity(matrix)

def recommendation(song_df):
    try:
        idx = df[df['song'] == song_df].index[0]
        # ... rest of your code using idx to calculate recommendations
    except KeyError:
        # Handle the case where the song is not found in the data
        print(f"Song '{song_df}' not found. No recommendations available.")
        return []  # Return an empty list to signal no recommendations
    
    idx = df[df['song'] == song_df].index[0]
    distances = sorted(list(enumerate(similarity[idx])),reverse=True,key=lambda x:x[1])
    songs = []
    # links =[]
    for m_id in distances[1:6]:
        songs.append(df.iloc[m_id[0]].song)
        # links.append(df.iloc[m_id[0]].link)
    return ','.join(songs[:5])

def recommendationlinks(song_df):
    try:
        idx = df[df['song'] == song_df].index[0]
        # ... rest of your code using idx to calculate recommendations
    except KeyError:
        # Handle the case where the song is not found in the data
        print(f"Song '{song_df}' not found. No recommendations available.")
        return []  # Return an empty list to signal no recommendations
    
    idx = df[df['song'] == song_df].index[0]
    distances = sorted(list(enumerate(similarity[idx])),reverse=True,key=lambda x:x[1])
    # songs = []
    links =[]
    for m_id in distances[1:6]:
        # songs.append(df.iloc[m_id[0]].song)
        links.append(df.iloc[m_id[0]].link)
    return ','.join(links[:5])

if __name__ == "__main__":
    search_term = sys.argv[1] if len(sys.argv) > 1 else ""
    recommendations= recommendation(search_term)
    recommendationlink= recommendationlinks(search_term)
    print(recommendations + ","+recommendationlink)
