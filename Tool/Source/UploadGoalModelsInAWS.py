import boto3
import os
# Create an S3 client
S3 = boto3.client('s3')
f = open("C:/goalmodelname/goalmodelname.txt", "r")
goalmodelname = f.read()
print(f.read())

directory = 'C:/'+goalmodelname+'/'
BUCKET_NAME = 'goalcontextrepository'
for filename in os.listdir(directory):
    if filename.endswith(".ndsl"):
        SOURCE_FILENAME = os.path.join(directory, filename)
        print(SOURCE_FILENAME)
        S3.upload_file(SOURCE_FILENAME, BUCKET_NAME, SOURCE_FILENAME)


# Uploads the given file using a managed uploader, which will split up large
# files automatically and upload parts in parallel.
#S3.upload_file(SOURCE_FILENAME, BUCKET_NAME, SOURCE_FILENAME)