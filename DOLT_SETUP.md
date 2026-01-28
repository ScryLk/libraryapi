# Configuração do Dolt para LibraryAPI

Este projeto foi configurado para usar o **Dolt**, um banco de dados SQL versionado que funciona como Git para seus dados.

## O que foi alterado

1. **pom.xml**: Substituído driver PostgreSQL pelo MySQL Connector (Dolt usa protocolo MySQL)
2. **application.yml**: Atualizado para usar configurações MySQL/Dolt na porta 3306
3. **init-dolt.sh**: Script criado para facilitar a inicialização do banco

## Como usar

### 1. Inicializar o banco de dados Dolt

Execute o script de inicialização:

```bash
./init-dolt.sh
```

Este script irá:
- Criar o diretório `~/dolt-databases/librarydb`
- Inicializar um repositório Dolt
- Configurar o usuário Git do Dolt
- Criar o commit inicial
- Opcionalmente iniciar o servidor Dolt

### 2. Iniciar o servidor Dolt manualmente

Se você não iniciou o servidor pelo script, execute:

```bash
cd ~/dolt-databases/librarydb
dolt sql-server --host 0.0.0.0 --port 3306
```

**Nota**: Nas versões mais recentes do Dolt, os parâmetros `--user` e `--password` foram removidos. Os usuários são criados via SQL com `CREATE USER` e `GRANT`.

### 3. Executar a aplicação Spring Boot

```bash
./mvnw spring-boot:run
```

## Gerenciamento de usuários

O script de inicialização cria automaticamente um usuário `root` sem senha. Para criar outros usuários:

```bash
# Conectar ao servidor Dolt
dolt sql

# Criar novo usuário
CREATE USER 'usuario'@'%' IDENTIFIED BY 'senha';

# Conceder permissões
GRANT ALL PRIVILEGES ON librarydb.* TO 'usuario'@'%';

# Aplicar alterações
FLUSH PRIVILEGES;
```

Para usar um usuário diferente na aplicação, atualize o [application.yml](src/main/resources/application.yml):

```yaml
spring:
  datasource:
    username: seu_usuario
    password: sua_senha
```

## Comandos úteis do Dolt

### Versionamento de dados

```bash
# Ver status das alterações
dolt status

# Ver diferenças nos dados
dolt diff

# Criar um commit das alterações
dolt add .
dolt commit -m "Sua mensagem de commit"

# Ver histórico de commits
dolt log

# Criar uma branch
dolt branch feature-branch

# Mudar para uma branch
dolt checkout feature-branch

# Fazer merge de branches
dolt merge feature-branch
```

### Consultas SQL

```bash
# Abrir console SQL
dolt sql

# Executar query direto
dolt sql -q "SELECT * FROM sua_tabela"
```

### Visualizar dados em diferentes versões

```bash
# Ver dados de um commit anterior
dolt sql -q "SELECT * FROM sua_tabela AS OF 'commit-hash'"

# Ver dados de uma branch específica
dolt checkout outra-branch
dolt sql -q "SELECT * FROM sua_tabela"
```

## Recursos adicionais

- [Documentação oficial do Dolt](https://docs.dolthub.com/)
- [Dolt vs Git](https://www.dolthub.com/blog/2020-03-06-so-you-want-git-database/)
- [SQL as Understood by Dolt](https://docs.dolthub.com/sql-reference/sql-support)

## Vantagens do Dolt

- **Versionamento completo**: Todo o histórico de alterações de dados
- **Branches**: Teste mudanças sem afetar dados de produção
- **Merge**: Combine alterações de diferentes branches
- **Rollback fácil**: Volte para qualquer versão anterior
- **Colaboração**: Clone, push, pull como no Git
- **SQL completo**: 100% compatível com MySQL

## Troubleshooting

### Erro: "--user and --password have been removed"

Se você ver este erro, significa que está tentando usar parâmetros obsoletos. Use o comando correto:

```bash
# ERRADO (versões antigas)
dolt sql-server --host 0.0.0.0 --port 3306 --user root

# CORRETO (versões atuais)
dolt sql-server --host 0.0.0.0 --port 3306
```

Os usuários devem ser criados via SQL:

```sql
CREATE USER 'root'@'%' IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```

### Porta 3306 já em uso

Se você já tem MySQL instalado, altere a porta no `application.yml` e ao iniciar o servidor:

```bash
dolt sql-server --host 0.0.0.0 --port 3307
```

E no [application.yml](src/main/resources/application.yml):
```yaml
url: jdbc:mysql://localhost:3307/librarydb
```

### Erro de conexão

Certifique-se de que o servidor Dolt está rodando:

```bash
ps aux | grep dolt
```

Se não estiver, inicie-o conforme instruções acima.

### Erro de autenticação

Se você receber erro de autenticação, verifique:

1. O usuário foi criado corretamente no Dolt
2. As credenciais no [application.yml](src/main/resources/application.yml) estão corretas
3. O servidor Dolt foi reiniciado após criar usuários
