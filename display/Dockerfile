# Base image
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Copy package.json dan package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy seluruh source code
COPY . .

# Build aplikasi Next.js
RUN npm run build

# Base image for production environment
FROM node:18-alpine AS runner

WORKDIR /app

# Copy build dari stage builder
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/src ./src
COPY --from=builder /app/next.config.mjs ./next.config.mjs

# Expose port untuk server Next.js
EXPOSE 3000

# Jalankan aplikasi
CMD ["npm", "run", "start"]
