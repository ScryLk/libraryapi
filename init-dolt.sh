#!/bin/bash

# Script para inicializar o banco de dados Dolt para o projeto LibraryAPI

echo "Iniciando configuração do Dolt..."

# Criar diretório para o banco de dados
mkdir -p ~/dolt-databases
cd ~/dolt-databases

# Verificar se o banco já existe
if [ -d "librarydb" ]; then
    echo "Banco de dados 'librarydb' já existe."
    read -p "Deseja recriá-lo? (s/n): " choice
    if [ "$choice" = "s" ] || [ "$choice" = "S" ]; then
        rm -rf librarydb
    else
        echo "Usando banco de dados existente."
        cd librarydb
        echo "Iniciando servidor Dolt..."
        dolt sql-server --host 0.0.0.0 --port 3306 &
        DOLT_PID=$!
        echo "Servidor Dolt iniciado na porta 3306 (PID: $DOLT_PID)"
        exit 0
    fi
fi

# Criar novo banco de dados Dolt
echo "Criando banco de dados 'librarydb'..."
mkdir librarydb
cd librarydb
dolt init

# Configurar usuário do Dolt (se ainda não configurado)
dolt config --global --add user.email "dev@libraryapi.com" 2>/dev/null || true
dolt config --global --add user.name "Library API Developer" 2>/dev/null || true

# Criar banco de dados e usuário
echo "Configurando banco de dados e usuário..."
dolt sql <<SQL
CREATE DATABASE IF NOT EXISTS librarydb;
USE librarydb;

-- Criar usuário root com senha vazia
CREATE USER 'root'@'%' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
SQL

# Criar commit inicial
dolt add .
dolt commit -m "Initial database setup with root user"

echo ""
echo "Banco de dados Dolt criado com sucesso!"
echo ""
echo "Para iniciar o servidor Dolt, execute:"
echo "  cd ~/dolt-databases/librarydb"
echo "  dolt sql-server --host 0.0.0.0 --port 3306"
echo ""
echo "Credenciais configuradas:"
echo "  Usuário: root"
echo "  Senha: (vazia)"
echo ""

# Perguntar se deseja iniciar o servidor agora
read -p "Deseja iniciar o servidor Dolt agora? (s/n): " start_server
if [ "$start_server" = "s" ] || [ "$start_server" = "S" ]; then
    echo "Iniciando servidor Dolt..."
    dolt sql-server --host 0.0.0.0 --port 3306 &
    DOLT_PID=$!
    echo "Servidor Dolt iniciado na porta 3306 (PID: $DOLT_PID)"
    echo ""
    echo "Para parar o servidor, execute: kill $DOLT_PID"
fi
