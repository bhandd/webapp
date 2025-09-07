1. build:
docker build -t my-webpage .
2. run:

docker run -d --rm --name my-webpage -p 8080:8080 -e ZOHO_MAIL_PASSWORD="ZOHO_MAIL_PASSWORD" my-webpage

3. stop and delete container
ctrl+c


the ZOHO_MAIL_PASSWORD is located in your zoho account settings under app passwords