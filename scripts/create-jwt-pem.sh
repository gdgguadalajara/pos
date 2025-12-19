#!/usr/bin/env bash
set -e

mkdir -p data

openssl genrsa -out data/rsaPrivateKey.pem 2048
openssl rsa -pubout -in data/rsaPrivateKey.pem -out data/publicKey.pem
openssl pkcs8 -topk8 -nocrypt -inform pem -in data/rsaPrivateKey.pem -outform pem -out data/privateKey.pem

echo "Claves generadas exitosamente en la carpeta ./data/"