import React, { useState } from 'react';

interface FileUploadProps {
  files: File[];
  onChange: (files: File[]) => void;
  accept: string;
  maxFiles: number;
  maxSize: number;
  label: string;
}

const FileUpload: React.FC<FileUploadProps> = ({
  files,
  onChange,
  accept,
  maxFiles,
  maxSize,
  label,
}) => {
  const [dragOver, setDragOver] = useState(false);
  const [error, setError] = useState('');

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
    setDragOver(true);
  };

  const handleDragLeave = (e: React.DragEvent) => {
    e.preventDefault();
    setDragOver(false);
  };

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setDragOver(false);
    const droppedFiles = Array.from(e.dataTransfer.files);
    handleFiles(droppedFiles);
  };

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = Array.from(e.target.files || []);
    handleFiles(selectedFiles);
  };

  const handleFiles = (newFiles: File[]) => {
    setError('');
    
    // Check total file count
    if (files.length + newFiles.length > maxFiles) {
      setError(`Maximum ${maxFiles} files allowed`);
      return;
    }

    // Check file sizes
    const oversizedFiles = newFiles.filter(file => file.size > maxSize);
    if (oversizedFiles.length > 0) {
      setError(`Some files are too large. Maximum size: ${maxSize / (1024 * 1024)}MB`);
      return;
    }

    // Check file types
    const validFiles = newFiles.filter(file => {
      const fileType = file.type;
      const fileName = file.name.toLowerCase();
      
      if (accept === 'image/*') {
        return fileType.startsWith('image/');
      } else if (accept === '.pdf,.doc,.docx') {
        return fileType === 'application/pdf' || 
               fileType === 'application/msword' ||
               fileType === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' ||
               fileName.endsWith('.pdf') ||
               fileName.endsWith('.doc') ||
               fileName.endsWith('.docx');
      }
      return false;
    });

    if (validFiles.length !== newFiles.length) {
      setError('Some files have invalid formats');
      return;
    }

    onChange([...files, ...validFiles]);
  };

  const removeFile = (index: number) => {
    const newFiles = files.filter((_, i) => i !== index);
    onChange(newFiles);
  };

  const formatFileSize = (bytes: number) => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  };

  return (
    <div className="w-full">
      <div
        className={`border-2 border-dashed rounded-lg p-6 text-center cursor-pointer transition-colors ${
          dragOver
            ? 'border-blue-500 bg-blue-50'
            : 'border-gray-300 hover:border-gray-400'
        }`}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
        onClick={() => document.getElementById('file-input')?.click()}
      >
        <input
          id="file-input"
          type="file"
          accept={accept}
          multiple
          className="hidden"
          onChange={handleFileSelect}
        />
        <div className="space-y-2">
          <div className="text-gray-600">
            <span className="font-medium">Click to upload</span> or drag and drop
          </div>
          <div className="text-sm text-gray-500">
            {label}
          </div>
          <div className="text-sm text-gray-500">
            Max {maxFiles} files, {maxSize / (1024 * 1024)}MB each
          </div>
        </div>
      </div>

      {error && (
        <div className="mt-2 text-sm text-red-600">{error}</div>
      )}

      {files.length > 0 && (
        <div className="mt-4 space-y-2">
          <h4 className="text-sm font-medium text-gray-700">Selected Files:</h4>
          {files.map((file, index) => (
            <div key={index} className="flex items-center justify-between bg-gray-50 p-2 rounded">
              <div className="flex items-center space-x-2">
                <span className="text-sm text-gray-600">{file.name}</span>
                <span className="text-xs text-gray-500">({formatFileSize(file.size)})</span>
              </div>
              <button
                type="button"
                onClick={() => removeFile(index)}
                className="text-red-600 hover:text-red-800 text-sm"
              >
                Remove
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default FileUpload;